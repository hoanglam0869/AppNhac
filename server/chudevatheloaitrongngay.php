<?php
	require "connect.php";

	class TheLoai {
		function TheLoai($idtheloai, $idkeychude, $tentheloai, $hinhtheloai) {
			$this->IdTheLoai = $idtheloai;
			$this->IdKeyChuDe = $idkeychude;
			$this->TenTheLoai = $tentheloai;
			$this->HinhTheLoai = $hinhtheloai;
		}
	}

	class ChuDe {
		function ChuDe($idchude, $tenchude, $hinhchude) {
			$this->IdChuDe = $idchude;
			$this->TenChuDe = $tenchude;
			$this->HinhChuDe = $hinhchude;
		}
	}

	$arraytheloai = array();
	$arraychude = array();

	$querytheloai = "SELECT DISTINCT * FROM Mp3theloai ORDER BY rand(" . date("Ymd") . ") LIMIT 4";
	$datatheloai = mysqli_query($con, $querytheloai);
	while ($row = mysqli_fetch_assoc($datatheloai)) {
		array_push($arraytheloai, new TheLoai($row['IdTheLoai'], $row['IdChuDe'], $row['TenTheLoai'], $row['HinhTheLoai']));
	}

	$querychude = "SELECT DISTINCT * FROM Mp3chude ORDER BY rand(" . date("Ymd") . ") LIMIT 4";
	$datachude = mysqli_query($con, $querychude);
	while ($row = mysqli_fetch_assoc($datachude)) {
		array_push($arraychude, new ChuDe($row['IdChuDe'], $row['TenChuDe'], $row['HinhChuDe']));
	}

	$arraychudetheloai = array('TheLoai' => $arraytheloai, 'ChuDe' => $arraychude);
	echo json_encode($arraychudetheloai);
?>