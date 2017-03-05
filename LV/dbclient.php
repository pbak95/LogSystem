<?php
include("connect.php");

$connection = @new mysqli($host, $db_user, $db_password, $db_name);
	
	if ($connection->connect_errno!=0)
	{
		echo "Error: ".$connection->connect_errno;
	}
	else
	{
		if (isset($_POST['operation'])) {
			$operation = $_POST['operation'];
			//echo $operation;
		}else{
			$operation = 'nothing';
			//$operation = 'searchStandard';
		}
	
		if($operation== 'searchStandard'){	
			
		$querry = 'select * from `logtype1` where 1 ';
		if (($_POST['date']) !='') {
		$querry.='AND `DateTime` LIKE "%'.$_POST['date'].'%"'; 	
		}if(($_POST["time"]) !='') {
		$querry.=' AND `DateTime` like "%'.$_POST["time"].'%"';  
		}if(($_POST["host"]) !='') {
		$querry.=' AND `Host`="'.$_POST["host"].'"';  
		}if (($_POST["priority"]) !='') {
		$querry.=' AND `Priority`="'.$_POST["priority"].'"';  
		}if(($_POST["data"]) !='') {
		$querry.=' AND `Data`="'.$_POST["data"].'"';  
		}
		//echo $querry;
		$result = $connection->query($querry);
		
		if ($result->num_rows > 0) {
		 echo '<table id="table_id">
		<thead>
        <tr>
			<th>Id</th>
            <th>Date&Time</th>
			<th>Priority</th>
			<th>Host</th>
			<th>Data</th>
			
        </tr>
    </thead>
	<tfoot>
        <tr>
			<th>Id</th>
            <th>Date&Time</th>
			<th>Priority</th>
			<th>Host</th>
			<th>Data</th>
			
        </tr>
    </tfoot>
	<tbody>
		';
		while($row = $result->fetch_assoc()) {
		echo'<tr>
		<td>'.$row['Id'].'</td>
		<td>'.$row['DateTime'].'</td>
		<td>'.$row['Priority'].'</td>
		<td>'.$row['Host'].'</td>
		<td>'.$row['Data'].'</td>
		</tr>';
		}
		echo '</tbody> </table>';
		
	} else {
		echo "<p>0 results</p>";
	}
	
		}else if($operation== 'getlist'){
		$sql = "SHOW TABLES FROM `la_database`";
		

				$result = mysqli_query($connection, $sql);
				 while ($row = $result->fetch_array(MYSQLI_NUM)) {
					 if($row[0] === 'logtype1'){
						 
					 }else if($row[0] === 'logtype2'){
						 
					 }else{
					echo '
					 <option>'.$row[0].'</option>
					 ';}
					
		}
		 mysqli_free_result($result);
	}else if($operation == 'searchNonstandard'){
		$sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '".$_POST['tablename']."'";
		
		
	    $result = mysqli_query($connection, $sql);
		$sqltemp = "SELECT * FROM `".$_POST['tablename']."` WHERE";
		while ($row = $result->fetch_array(MYSQLI_NUM)) {
					$sqltemp.=" ".$row[0]." LIKE '%".$_POST['phrase']."%' OR";
					
		} 
		
		 $sql2=substr($sqltemp, 0, -2);
		 //echo $sql2;
		$result = mysqli_query($connection, $sql2);
		$data = mysqli_fetch_all($result,MYSQLI_ASSOC);
		
		 echo '<table id="table_id">
		<thead>
        <tr>';
		foreach($data as $key => $value){
			foreach ($value as $key2 => $value2) {
            echo "<th>$key2</th>";
        }
		break;
		}
		echo '</tr>
    </thead>';
	
	echo '<tfoot>
        <tr>';
		foreach($data as $key => $value){
			foreach ($value as $key2 => $value2) {
            echo "<th>$key2</th>";
        }
		break;
		}
		echo '</tr>
    </tfoot>';
	
	echo '<tbody>
		';
		
		 foreach ($data as $key => $value) {
        echo "<tr>";
        foreach ($value as $key2 => $value2) {
            echo "<td>$value2</td>";
        }
        echo "</tr>";
    }
		echo '</tbody></table>';
		 mysqli_free_result($result);
	}else if($operation == 'remove'){
		
		 $result = mysqli_query($connection, $_POST['statement']);
	}
	
		$connection->close();
	}

?>