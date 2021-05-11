<?php
	require "connect.php";

	$query = "SELECT Mp3quangcao.Id, Mp3quangcao.Hinhanh, Mp3quangcao.Noidung, Mp3quangcao.Idbaihat, Mp3baihat.TenBaiHat, Mp3baihat.HinhBaiHat FROM Mp3baihat INNER JOIN Mp3quangcao ON Mp3quangcao.Idbaihat = Mp3baihat.IdBaiHat WHERE Mp3quangcao.Idbaihat = Mp3baihat.IdBaiHat";
	$data = mysqli_query($con, $query);

	class QuangCao {
		function QuangCao($idquangcao, $hinhanh, $noidung, $idbaihat, $tenbaihat, $hinhbaihat) {
			$this->IdQuangCao = $idquangcao;
			$this->Hinhanh = $hinhanh;
			$this->Noidung = $noidung;
			$this->IdBaiHat = $idbaihat;
			$this->TenBaiHat = $tenbaihat;
			$this->HinhBaiHat = $hinhbaihat;
		}
	}
	
	$mangquangcao = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangquangcao, new QuangCao($row['Id'], $row['Hinhanh'], $row['Noidung'], $row['Idbaihat'], $row['TenBaiHat'], $row['HinhBaiHat']));
	}

	echo json_encode($mangquangcao);
?>