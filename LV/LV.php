
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
	<script src="js/LV.js" ></script>
    <script src="js/jquery-3.1.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="LV.css"/>
	 <!-- DataTables -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.12/b-colvis-1.2.2/b-flash-1.2.2/sc-1.4.2/se-1.2.0/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.12/b-colvis-1.2.2/b-flash-1.2.2/sc-1.4.2/se-1.2.0/datatables.min.js"></script>

   <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Log Viewer</a>
        </div>
      
      </div>
    </nav>
	
	<div>
	<br><br><br>
	
	<div class="container">

     <div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Select logs</h3>
  </div>
  <div class="panel-body">
  <div class="form-group">
	<div class="row">
	 <div class="col-sm-4">
  <label class="radio-inline">
      <input type="radio" name="optradio" value="logtype1">Standard log type 1
    </label>
</div>
 <div class="col-sm-4">
 
</div>
 <div class="col-sm-4">
  <label class="radio-inline">
      <input type="radio" name="optradio" value="logs">Nonstandard log types
    </label>
</div>
	</div>
	<div class="row">
	 <div class="col-sm-4">
</div>
 <div class="col-sm-4">
</div>
	<div class="selectDiv">
 <div class="col-sm-4">
   <select class="form-control" id="log_types" style="display: none">
</select>

	  </div>
</div>
	</div>
  </div>
</div>
	</div>
    </div><!-- /.container -->

	<br>
	<div class="container">

     <div class="panel panel-default" id="search_logs" style="display: none">
  <div class="panel-heading">
    <h3 class="panel-title">Search by phrase</h3>
  </div>
  <div class="panel-body">
  <div class="form-group">
	<div class="row">
	 <div class="col-sm-1">
</div>
 <div class="col-sm-7">
<div class="col-sm-3">
<label for="phrase">Enter phrase:</label>
</div>
<div class="col-sm-9">
 <input class="form-control" id="phrase" type="text">
</div>
</div>
 <div class="col-sm-4">
 <button type="button"  class="btn btn-primary btn-lg center-block" id="searchnonstandard">Search</button>
</div>
	</div>
  </div>
</div>
</div>
    </div><!-- /.container -->
	
	<br>
    <div class="container">

     <div class="panel panel-default" id="search" style="display: none">
  <div class="panel-heading">
    <h3 class="panel-title">Search parameters</h3>
  </div>
  <div class="panel-body">
  <div class="form-group">
  <div class="row">
  <div class="col-sm-6">
  <div class="col-sm-2">
 <label for="date">Date:</label>
 </div>
 <div class="col-sm-6">
      <input class="form-control" id="date" type="text">
	  </div>
</div>

<div class="col-sm-6">
  <div class="col-sm-2">
 <label for="time">Time:</label>
 </div>
 <div class="col-sm-6">
      <input class="form-control" id="time" type="text">
	  </div>
</div>

</div>

<br><br>

  <div class="row">
  
  <div class="col-sm-6">
  <div class="col-sm-2">
 <label for="data">Host:</label>
 </div>
 <div class="col-sm-6">
      <input class="form-control" id="host" type="text">
	  </div>
</div>
  
  <div class="col-sm-6">
  <div class="col-sm-2">
 <label for="priority">Priority:</label>
 </div>
 <div class="col-sm-6">
	<div class="selectDiv">
   <select class="form-control" id="priority">
  <option value='None'></option>
  <option>DISEASTER</option>
  <option>CRITICAL</option>
  <option>HIGH</option>
  <option>WARNING</option>
</select>
	  </div>
</div>
</div>
</div>

<br><br>
<div class="row">
  <div class="col-sm-6">
  <div class="col-sm-2">
 <label for="data">Data:</label>
 </div>
 <div class="col-sm-6">
      <input class="form-control" id="data" type="text">
	  </div>
</div>
  <div class="col-sm-6">
</div>
</div>


<br>
<div class="row">
<button type="button"  class="btn btn-primary btn-lg center-block" id="searchstandard">Search</button>
</div>

  </div>
</div>
</div>
    </div><!-- /.container -->
	
	
	<div class="container">
	<div class="panel panel-default" id="result" style="display: none">
  <div class="panel-heading">
    <h3 class="panel-title">Result</h3>
  </div>
  <div class="panel-body">
  <div class="row">
	 <div class="col-sm-4">
	  <button type="button" class="btn btn-danger" id="deleteButton">Delete selected rows</button>
</div>
 <div class="col-sm-4">
</div>
	
 <div class="col-sm-4">
 
	  </div>
	</div>
	<br>
	<div id="responsecontainer" align="center">
	<table id="table_id" class="display">
	</table>
</div>
  
  </div>
</div>

    </div><!-- /.container -->
	

 <script type="text/javascript">
 $(document).ready(function() {
	  	 
	 ready();
	  
	    $('input[type=radio][name=optradio]').change(function() {
        if (this.value == 'logtype1') {
			$("#search").show();
			$("#search_logs").hide();
			$("#log_types").hide();
			$('#priority').removeAttr('disabled');
			        }
       else if (this.value == 'logs') {
			$("#search").hide();
			$("#search_logs").show();
			getLogNames();
			
        }
    });
	 
});
</script>
	
	
   
  </body>
</html>
