
var API_URL = "https://script.google.com/macros/s/AKfycby677qxY4tbBfjjDnwjFSCJ00N1yo4iu0qDKCr21qXhLdKTEIuo/exec?bulan="; 

function initDate(){
    document.getElementById('exportbutton').style.visibility = "hidden";
	
    var currentYear = (new Date()).getFullYear();
    var currentMonth = new Date().getMonth();
    var monthArray = [ "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "Desember" ];
    
    for (y = currentYear-5; y < currentYear+5; y++){
        var optn = document.createElement("OPTION");
        optn.text = y;
        optn.value = y;
     
        if (y == currentYear){
            optn.selected = true;
        }
        
        document.getElementById('tahun').options.add(optn);
    }
    
    for(m = 0; m <= 11; m++) {
        var optn = document.createElement("OPTION");
        optn.text = monthArray[m];
        optn.value = (m+1);
     
        if ( m == currentMonth-1) {
            optn.selected = true;
        }
     
        document.getElementById('bulan').options.add(optn);
    }

    var optn = document.createElement("OPTION");
        optn.text = "Tgl 1 s/d 31";
        optn.value = 0;
        document.getElementById('tgl').options.add(optn);

    for(m = 1; m <= 31; m++) {
        var optn = document.createElement("OPTION");
        optn.text = m;
        optn.value = m;     
        document.getElementById('tgl').options.add(optn);
    }

}

function loadData() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById("loader").style.visibility = "hidden";
        
        var myObj = JSON.parse(this.responseText);

        var col = [];
        for (var i = 0; i < myObj.length; i++) {
            for (var key in myObj[i]) {
                if (col.indexOf(key) === -1) {
                    col.push(key);
                }
            }
        }

        // CREATE DYNAMIC TABLE.
        var table = document.createElement("table");
        table.setAttribute("id", "mytable");
        table.classList.add('mytable');
        table.style = "width:600px;"

        // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.
        var tr = table.insertRow(-1);               // TABLE ROW.

        var th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = "No";
            th.style = "width:10px;"
            tr.appendChild(th);

        var th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = "Nama";
            th.style = "width:390px;"
            tr.appendChild(th);
        
        var th = document.createElement("th");      // TABLE HEADER.
            th.innerHTML = "Jumlah";
            th.style = "width:200px;"
            tr.appendChild(th);

        // ADD JSON DATA TO THE TABLE AS ROWS.
        for (var i = 0; i < myObj.length; i++) {

            tr = table.insertRow(-1);
            tr.insertCell(-1).innerHTML = i + 1;
            for (var j = 0; j < col.length; j++) {
                tr.insertCell(-1).innerHTML = myObj[i][col[j]];
            }
        }
        
        var divContainer = document.getElementById("showData");
        divContainer.innerHTML = "";
        divContainer.appendChild(table);
        document.getElementById('exportbutton').style.visibility = "visible";
        
    }
   
  };
    
  var e = document.getElementById("bulan");
  var bulan = e.options[e.selectedIndex].value;
  var f = document.getElementById("tahun");
  var tahun = f.options[f.selectedIndex].value;
  var g = document.getElementById("tgl");
  var tgl = g.options[g.selectedIndex].value;

  var url =  API_URL + bulan + "&tahun=" + tahun;

  if (tgl > 0){
  	url = url + "&tgl="+tgl;
  }

  xhttp.open("GET",url, true);
  xhttp.send();
  document.getElementById("loader").style.visibility = "visible";
}

function exportTableToExcel(tableID, filename = ''){
    var downloadLink;
    
    //var dataType = 'application/vnd.ms-excel';
    var dataType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
    var tableSelect = document.getElementById(tableID);
    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
    
    // Specify file name
    filename = filename?filename+'.xls':'excel_data.xls';
    
    // Create download link element
    downloadLink = document.createElement("a");
    
    document.body.appendChild(downloadLink);
    
    if(navigator.msSaveOrOpenBlob){
        var blob = new Blob(['\ufeff', tableHTML], {
            type: dataType
        });
        navigator.msSaveOrOpenBlob( blob, filename);
    }else{
        // Create a link to the file
        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
    
        // Setting the file name
        downloadLink.download = filename;
        
        //triggering the function
        downloadLink.click();
    }
}

function switchMenu(){
   var opttgl = document.getElementById("tgl");
   var judul = document.getElementById("judul");

   if (opttgl.options[opttgl.selectedIndex].value > 0){
        judul.innerHTML = "Laporan Harian"
   } else {
        judul.innerHTML = "Laporan Bulanan"
   }
}