<?php
	require "connect.php";

	$query = "SELECT DISTINCT * FROM Mp3playlist ORDER BY rand(" . date("Ymd") . ") LIMIT 3";
	class PlayListToday() {
		function PlayListToday($idplaylist, $ten, $hinh, $icon) {
			$this->IdPlaylist = $idplaylist;
			$this->Ten = $ten;
			$this->HinhPlaylist = $hinh;
			$this->Icon = $icon;
		}
	}

	$arrayplaylistfortoday = array();
	$data = mysqli_query($con, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrayplaylistfortoday, new PlayListToday($row['IdPlayList'], $row['Ten'], $row['Hinhnen'], $row['Hinhicon']));
	}

	echo json_encode($arrayplaylistfortoday);
?>