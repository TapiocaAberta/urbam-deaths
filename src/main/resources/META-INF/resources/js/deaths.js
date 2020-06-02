google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(drawChart);
google.charts.setOnLoadCallback(drawChart2);
      
      var values = [["JANEIRO", 0, 0],
   	  				["FEVEREIRO", 0, 0],
   	  				["MARCO", 0, 0],
   	  				["ABRIL", 0, 0],
   	  				["MAIO", 0, 0],
   	  			    ["JUNHO", 0, 0],
   	  				["JULHO", 0, 0],
   	  				["AGOSTO", 0, 0],
   	  				["SETEMBRO", 0, 0],
   	  				["OUTUBRO", 0, 0],
   	  				["NOVEMBRO", 0, 0],
   	  				["DEZEMBRO", 0, 0]];
      
      var daysData = [];
      
      function callEndpoint() {
    	    $.getJSON('/api/deaths/direto/compare', function(data) {
    	    	
    	    	for(var i = 0; i < values.length; i++) {
    	    		for(var j = 0 in data) {
    	    			if(values[i][0] === data[j].month) {
    	    				
    	    				if(data[j].year === '2019') 
    	    					values[i][1] = data[j].count;
    	    				else
    	    					values[i][2] = data[j].count;
		    	        	
    	    			}
    	    		}
    	    	}
    	    });
      }
      
      function callEndpoint2() {
  	    $.getJSON('/api/deaths/direto/days/last-mount/compare', function(data) {
  	    	this.daysData = data;
  	    });
    }
      
      callEndpoint();
      callEndpoint2();
      
      function drawChart() {
    	  
    	   for(var i = 0; i < values.length; i++) {
    		  if(values[i][1] === 0 || values[i][2] === 0) {
    			  values.splice(i, 1);
    			  i--;
    		  }
    	  }

          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Mês');
          data.addColumn('number', '2019');
          data.addColumn('number', '2020');

          data.addRows(values);

          var options = {
            chart: {
              title: 'Óbitos por mês registrados na Urbam',
              subtitle: 'Com funeral Direto (sem velório) excluindo natimortos e recém nacidos'
            },
            width: 900,
            height: 500,
            axes: {
              x: {
                0: {side: 'botton'}
              }
            }
          };

          var chart = new google.charts.Line(document.getElementById('chart_months'));
          chart.draw(data, google.charts.Line.convertOptions(options));
        }
      
      function drawChart2() {
    	  
         var data = new google.visualization.DataTable();
         data.addColumn('number', 'Dia');
         data.addColumn('number', '2019');
         data.addColumn('number', '2020');

         data.addRows(daysData);

         var options = {
           chart: {
             title: 'Óbitos por dia do mês anterior registrados na Urbam',
             subtitle: 'Com funeral Direto (sem velório) excluindo natimortos e recém nacidos'
           },
           width: 900,
           height: 500,
           axes: {
             x: {
               0: {side: 'botton'}
             }
           }
         };

         var chart = new google.charts.Line(document.getElementById('chart_months_days'));
         chart.draw(data, google.charts.Line.convertOptions(options));
       }
      