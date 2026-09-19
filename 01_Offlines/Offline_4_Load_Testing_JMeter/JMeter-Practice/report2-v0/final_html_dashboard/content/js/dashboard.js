/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 46.4, "KoPercent": 53.6};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.464, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.34, 500, 1500, "/api/download/256"], "isController": false}, {"data": [0.94, 500, 1500, "/login"], "isController": false}, {"data": [0.0, 500, 1500, "/courses"], "isController": false}, {"data": [0.14, 500, 1500, "/notices"], "isController": false}, {"data": [0.9, 500, 1500, "/"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 250, 134, 53.6, 324.88000000000005, 15, 2834, 225.0, 773.2000000000003, 1114.85, 1560.7400000000011, 2.5285472990057754, 157.20798360363506, 0.9664779422176372], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["/api/download/256", 50, 33, 66.0, 576.7799999999999, 60, 2834, 306.5, 1341.0, 1555.6999999999994, 2834.0, 0.5174697797648617, 132.61325392112727, 0.1859657021029972], "isController": false}, {"data": ["/login", 50, 3, 6.0, 67.59999999999998, 15, 617, 40.0, 106.29999999999995, 328.64999999999907, 617.0, 0.5169080627319624, 0.7799052313680489, 0.22109934714511678], "isController": false}, {"data": ["/courses", 50, 50, 100.0, 428.72, 210, 1241, 360.5, 696.5999999999999, 1030.1499999999992, 1241.0, 0.5133944615005492, 22.263972511577045, 0.20204879686007945], "isController": false}, {"data": ["/notices", 50, 43, 86.0, 365.7000000000001, 127, 1106, 383.5, 536.9, 601.65, 1106.0, 0.5092479426383117, 3.7387949538621363, 0.19693572781715962], "isController": false}, {"data": ["/", 50, 5, 10.0, 185.60000000000002, 40, 1309, 95.0, 216.09999999999997, 1116.1999999999998, 1309.0, 0.5096424348676968, 1.211396178191381, 0.17518958698577078], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 410 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 308 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 898 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 288 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,106 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 370 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 611 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 474 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 251 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 318 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 395 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 231 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 648 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 363 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 482 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 360 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,309 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 462 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 343 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 578 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 340 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 777 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,438 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 353 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 382 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 243 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 305 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 409 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 961 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 330 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,341 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 640 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,109 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 239 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 2,834 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 491 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 218 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 470 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 286 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,200 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,063 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 290 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 233 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 471 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 481 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 500 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 739 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 374 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 335 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 836 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,108 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 412 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 262 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 384 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 203 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 277 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 229 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 480 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 364 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 281 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,122 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 451 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,064 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 968 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 544 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 448 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 431 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 535 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 267 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 845 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,271 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 453 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 230 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 289 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 426 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 421 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 617 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 312 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 416 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 272 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 225 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 356 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 1,499 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 443 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 436 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 433 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 314 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 208 milliseconds, but should not have lasted longer than 200 milliseconds.", 3, 2.2388059701492535, 1.2], "isController": false}, {"data": ["The operation lasted too long: It took 465 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 594 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 709 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 292 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 610 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 702 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 372 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,625 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 424 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 832 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 351 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.492537313432836, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 444 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 275 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 254 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 434 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 955 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 541 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 502 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 206 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 489 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 265 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,125 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 361 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 226 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,241 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 210 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 663 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.746268656716418, 0.4], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 250, 134, "The operation lasted too long: It took 208 milliseconds, but should not have lasted longer than 200 milliseconds.", 3, "The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 318 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 395 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 231 milliseconds, but should not have lasted longer than 200 milliseconds.", 2], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["/api/download/256", 50, 33, "The operation lasted too long: It took 1,341 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 243 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 308 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 305 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 535 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/login", 50, 3, "The operation lasted too long: It took 448 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 231 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 617 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "", "", "", ""], "isController": false}, {"data": ["/courses", 50, 50, "The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 318 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 356 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 410 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 305 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/notices", 50, 43, "The operation lasted too long: It took 233 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 395 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 208 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 243 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 431 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/", 50, 5, "The operation lasted too long: It took 1,125 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,108 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,109 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,309 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 218 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
