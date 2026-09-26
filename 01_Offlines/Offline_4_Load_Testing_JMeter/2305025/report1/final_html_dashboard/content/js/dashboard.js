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

    var data = {"OkPercent": 43.2, "KoPercent": 56.8};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.432, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.28, 500, 1500, "/api/download/256"], "isController": false}, {"data": [0.94, 500, 1500, "/login"], "isController": false}, {"data": [0.0, 500, 1500, "/courses"], "isController": false}, {"data": [0.18, 500, 1500, "/notices"], "isController": false}, {"data": [0.76, 500, 1500, "/"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 250, 142, 56.8, 361.46399999999977, 14, 2237, 227.5, 841.2, 1160.5999999999995, 2132.4000000000005, 2.4989754200777683, 155.36940401310463, 0.9551747845883188], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["/api/download/256", 50, 36, 72.0, 591.4599999999999, 54, 2237, 390.5, 1599.6999999999998, 2129.9999999999995, 2237.0, 0.5125891905191503, 131.3624933043037, 0.18421174034281965], "isController": false}, {"data": ["/login", 50, 3, 6.0, 71.12000000000002, 14, 550, 43.0, 92.49999999999997, 354.3499999999988, 550.0, 0.5159852221832367, 0.7785128596416998, 0.22070461651978288], "isController": false}, {"data": ["/courses", 50, 50, 100.0, 443.86000000000007, 213, 1278, 389.5, 810.6999999999998, 1143.5499999999997, 1278.0, 0.5119541289100497, 22.20151074463728, 0.2014819472175293], "isController": false}, {"data": ["/notices", 50, 41, 82.0, 380.50000000000006, 125, 856, 378.5, 680.9, 768.05, 856.0, 0.5120432574143864, 3.7593175871497624, 0.19801672845321974], "isController": false}, {"data": ["/", 50, 12, 24.0, 320.38000000000005, 21, 1615, 113.0, 1134.8999999999999, 1335.8999999999994, 1615.0, 0.509803521722728, 1.211779074094844, 0.17524496059218778], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 675 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 469 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 1,612 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 236 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 459 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 380 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 685 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 891 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,066 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 856 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,116 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 509 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 566 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 355 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 214 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 437 milliseconds, but should not have lasted longer than 200 milliseconds.", 3, 2.112676056338028, 1.2], "isController": false}, {"data": ["The operation lasted too long: It took 293 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 789 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 764 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 321 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 440 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 363 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 360 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 430 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 311 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 735 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 353 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 263 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 243 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 2,163 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 379 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 393 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 491 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 238 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 286 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 290 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 471 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 481 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,187 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 296 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 280 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,336 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 228 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 500 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 389 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 408 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 413 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 213 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 2,237 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 335 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 248 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 583 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,108 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 252 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 987 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 490 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 534 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 223 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,263 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 209 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 203 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 432 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 277 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,006 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 631 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 422 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 386 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 550 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 535 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 267 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 250 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 215 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 644 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,278 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 406 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 2,103 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,137 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,489 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 376 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 421 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 458 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 773 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 225 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 463 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 401 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 684 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 359 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 269 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 398 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 227 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 691 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,425 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 366 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 575 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 510 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 1.408450704225352, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 468 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 208 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 205 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,139 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,817 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 475 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 260 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 825 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 532 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 285 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,615 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 682 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 712 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 217 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 378 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 688 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 216 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 255 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 338 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 464 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 576 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 367 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 1,105 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 415 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 508 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 518 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 483 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 322 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 361 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 843 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 226 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 926 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 210 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 0.704225352112676, 0.4], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 250, 142, "The operation lasted too long: It took 437 milliseconds, but should not have lasted longer than 200 milliseconds.", 3, "The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 789 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 238 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 408 milliseconds, but should not have lasted longer than 200 milliseconds.", 2], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["/api/download/256", 50, 36, "The operation lasted too long: It took 215 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 789 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 675 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 2,163 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,612 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/login", 50, 3, "The operation lasted too long: It took 550 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 227 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 510 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "", "", "", ""], "isController": false}, {"data": ["/courses", 50, 50, "The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 408 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 532 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 386 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 535 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/notices", 50, 41, "The operation lasted too long: It took 458 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 469 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 243 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 285 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 379 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/", 50, 12, "The operation lasted too long: It took 1,116 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 208 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,137 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 248 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 1,139 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
