

function ready(){

var selectedId = new Array();
	
 $("#searchstandard").click(function(){
	  	  search();
	
    }); 
	
  $("#searchnonstandard").click(function(){
	  	  search2($( "#log_types option:selected" ).text());
    }); 
	
function clearVals(){
	$('#date').val('');
	$('#time').val('');
	$('#host').val('');
	$('#priority').val('None');
	$('#data').val('');
}
	
function search(){
	
	var selectedTableName=$('input[name=optradio]:checked').val();
	
	var values = {
	"operation": 'searchStandard',	
	"date": ''+$("#date").val()+'',	
	"time": ''+$("#time").val()+'',	
	"host": ''+$("#host").val()+'',
	"priority": ''+$("#priority :selected").text()+'',
	"data":  ''+$("#data").val()+''
	}
	
	
	 $.ajax({ type: "POST",   
         url: "dbclient.php",
		 data: values,		 
         async: false,
         success : function(text)
         {
			if(text== '<p>0 results</p>'){
				  $("#deleteButton").hide();
			 }else{
				  $("#deleteButton").show();
			 }
             $("#responsecontainer").empty();
             document.getElementById("responsecontainer").innerHTML = text;
			 var table =$('#table_id').DataTable();
			 table.destroy();
			
			$('#table_id tbody').on( 'click', 'tr', function () {
				if ( $(this).hasClass('selected') ) {
				$(this).removeClass('selected');
				var index = selectedId.indexOf($(this).children(":first").text());
				if (index > -1) {
					selectedId.splice(index, 1);
				}
			}
			else {
            table.$('tr.selected')//.removeClass('selected');
            $(this).addClass('selected');
			 var $name = $(this).children(":first").text();
			selectedId.push($name);
			}
			} );
			
			
			$('#deleteButton').click( function() {
			var command = 'DELETE FROM `'+selectedTableName+'` WHERE `Id` in(';
			for(i=0; i< selectedId.length; i++){
					command=command + selectedId[i]+',';
				}
				command = command.slice(0, -1);
				command=command+')'
				//alert(command);
			
			var values = {
			'operation':'remove',
			'statement': command
			}
	
	
			$.ajax({ type: "POST",   
         url: "dbclient.php",
		 data: values,		 
         async: false,
         success : function()
         {	
				var rows = table
			.rows( '.selected' )
			.remove()
			.draw();
         },
		 failure: function(errMsg) {
        alert(errMsg);
    }
    });
			
			
			
				 } );
			
			
			
			 table = $('#table_id').DataTable();
			 clearVals();
			 
			  $('#table_id tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
	
	table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
			 
			 
		 
			 
         },
		 failure: function(errMsg) {
        alert(errMsg);
    }
    });
	
	
	$("#result").show();
	
};


function search2( tablename){
var selectedTableName=$("#log_types option:selected").text();
	
var values = {
	"operation": 'searchNonstandard',
	"tablename": tablename,
	"phrase": ''+$("#phrase").val()+''
	}
	
	
	
	 $.ajax({ type: "POST",   
         url: "dbclient.php",
		 data: values,		 
         async: false,
         success : function(text)
         {
			 if(text== '<p>0 results</p>'){
				  $("#deleteButton").hide();
			 }else{
				  $("#deleteButton").show();
			 }
			 $("#responsecontainer").empty();
             document.getElementById("responsecontainer").innerHTML = text;
			 var table =$('#table_id').DataTable();
			 table.destroy();
			 /////////////////
			 
			 $('#table_id tbody').on( 'click', 'tr', function () {
				if ( $(this).hasClass('selected') ) {
				$(this).removeClass('selected');
				var index = selectedId.indexOf($(this).children(":first").text());
				if (index > -1) {
					selectedId.splice(index, 1);
				}
			}
			else {
            table.$('tr.selected')//.removeClass('selected');
            $(this).addClass('selected');
			 var $name = $(this).children(":first").text();
			selectedId.push($name);
			}
			} );
			
			
			$('#deleteButton').click( function() {
			var command = 'DELETE FROM `'+selectedTableName+'` WHERE `Id` in(';
			for(i=0; i< selectedId.length; i++){
					command=command + selectedId[i]+',';
				}
				command = command.slice(0, -1);
				command=command+')'
				//alert(command);
			
			var values = {
			'operation':'remove',
			'statement': command
			}
	
	
			$.ajax({ type: "POST",   
         url: "dbclient.php",
		 data: values,		 
         async: false,
         success : function()
         {	
				var rows = table
			.rows( '.selected' )
			.remove()
			.draw();
         },
		 failure: function(errMsg) {
        alert(errMsg);
    }
    });
			
			
			
				 } );
			 /////////////////
			 table = $('#table_id').DataTable();
			 $('#phrase').val(''); 
				
				
			   $('#table_id tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
	
	table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
			 
			 			 
			 
			 
         },
		 failure: function(errMsg) {
        alert(errMsg);
    }
    });	
	$("#result").show();
}

}
function getLogNames(){
	var values = {
	'operation':'getlist'
	}
	
	
	 $.ajax({ type: "POST",   
         url: "dbclient.php",
		 data: values,		 
         async: false,
         success : function(text)
         {	
				//alert(text);
             document.getElementById("log_types").innerHTML = text;
			 $("#log_types").show();
         },
		 failure: function(errMsg) {
        alert(errMsg);
    }
    });
	
}
