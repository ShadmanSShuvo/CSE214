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

    var data = {"OkPercent": 65.6, "KoPercent": 34.4};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.656, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "/-1"], "isController": false}, {"data": [0.34, 500, 1500, "/notices-11"], "isController": false}, {"data": [0.82, 500, 1500, "/api/download/256-22"], "isController": false}, {"data": [0.12, 500, 1500, "/courses-13"], "isController": false}, {"data": [1.0, 500, 1500, "/login-17"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 250, 86, 34.4, 151.7640000000001, 5, 901, 78.5, 367.8, 444.34999999999997, 594.9300000000005, 2.5388185354063633, 157.84657967193385, 0.9684204296188725], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["/-1", 50, 0, 0.0, 48.54, 22, 131, 45.0, 75.9, 106.04999999999987, 131.0, 0.5101728465604147, 1.212656941921923, 0.17537191600514254], "isController": false}, {"data": ["/notices-11", 50, 33, 66.0, 279.56000000000006, 114, 501, 233.0, 452.8, 493.79999999999995, 501.0, 0.5099907181689294, 3.744248260931651, 0.19722297304189065], "isController": false}, {"data": ["/api/download/256-22", 50, 9, 18.0, 144.33999999999995, 39, 901, 72.5, 408.6, 592.6499999999997, 901.0, 0.5123895800455002, 131.31133863699247, 0.18414000532885164], "isController": false}, {"data": ["/courses-13", 50, 44, 88.0, 276.17999999999995, 185, 424, 281.0, 352.5, 386.84999999999985, 424.0, 0.5109078833086395, 22.156139037194094, 0.20107019235681808], "isController": false}, {"data": ["/login-17", 50, 0, 0.0, 10.200000000000001, 5, 41, 8.0, 17.9, 22.24999999999998, 41.0, 0.513015195510091, 0.7740317158819244, 0.2174302684095503], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 282 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 567 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 390 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 333 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 250 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 319 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 354 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 901 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 420 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 334 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 453 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 339 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 235 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 447 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 214 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 251 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 318 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 246 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 225 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 234 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 359 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 443 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 227 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 224 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 366 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 247 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 205 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 221 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 202 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 340 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 446 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 353 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 409 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 476 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 358 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 372 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 424 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 501 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 232 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 217 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 492 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 212 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 352 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 315 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 320 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 336 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 280 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 413 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 368 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 213 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 496 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 624 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 335 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 252 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 412 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 220 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 223 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 209 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 203 milliseconds, but should not have lasted longer than 200 milliseconds.", 4, 4.651162790697675, 1.6], "isController": false}, {"data": ["The operation lasted too long: It took 310 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 294 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 387 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 229 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 348 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 345 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 451 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 245 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, 2.3255813953488373, 0.8], "isController": false}, {"data": ["The operation lasted too long: It took 242 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 428 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 332 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 306 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 544 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}, {"data": ["The operation lasted too long: It took 287 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, 1.1627906976744187, 0.4], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 250, 86, "The operation lasted too long: It took 203 milliseconds, but should not have lasted longer than 200 milliseconds.", 4, "The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 234 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 227 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 320 milliseconds, but should not have lasted longer than 200 milliseconds.", 2], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["/notices-11", 50, 33, "The operation lasted too long: It took 213 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 252 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 390 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 250 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 476 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/api/download/256-22", 50, 9, "The operation lasted too long: It took 405 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 624 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 409 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 567 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 209 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": ["/courses-13", 50, 44, "The operation lasted too long: It took 320 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 203 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 245 milliseconds, but should not have lasted longer than 200 milliseconds.", 2, "The operation lasted too long: It took 282 milliseconds, but should not have lasted longer than 200 milliseconds.", 1, "The operation lasted too long: It took 323 milliseconds, but should not have lasted longer than 200 milliseconds.", 1], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
