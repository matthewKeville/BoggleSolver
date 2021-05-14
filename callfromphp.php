<?php
echo "calling from php";
$result = shell_exec('java -cp bin BoggleService make 4');
echo $result;
?>
