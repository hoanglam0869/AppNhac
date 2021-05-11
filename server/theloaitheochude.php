<?php
	require "connect.php";

	class TheLoai {
		function TheLoai($idtheloai, $Idchude, $tentheloai, $hinhtheloai) {
			$this->IdTheLoai = $idtheloai;
			$this->IdKeyChuDe = $Idchude;
			$this->TenTheLoai = $tentheloai;
			$this->HinhTheLoai = $hinhtheloai;
		}
	}

	$arraytheloai = array();

	$idchude = $_POST['idchude'];
	$query = "SELECT * FROM Mp3theloai WHERE IdChuDe = '$idchude'";
	$data = mysqli_query($con, $query);

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arraytheloai, new TheLoai($row['IdTheLoai'], $row['IdChuDe'], $row['TenTheLoai'], $row['HinhTheLoai']));
	}

	echo json_encode($arraytheloai);
?>