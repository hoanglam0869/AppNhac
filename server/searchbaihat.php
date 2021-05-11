<?php
	require "connect.php";

	class BaiHat {
		function BaiHat($idbaihat, $tenbaihat, $hinhbaihat, $casi, $linkbaihat, $luotthich) {
			$this->Idbaihat = $idbaihat;
			$this->Tenbaihat = $tenbaihat;
			$this->Hinhbaihat = $hinhbaihat;
			$this->Casi = $casi;
			$this->Linkbaihat = $linkbaihat;
			$this->Luotthich = $luotthich;
		}
	}

	$mangcakhuc = array();

	if (isset($_POST['tukhoa'])) {
		$tukhoa = $_POST['tukhoa'];
		$query = "SELECT * FROM Mp3baihat WHERE lower(TenBaiHat) LIKE '%$tukhoa%'";
		$data = mysqli_query($con, $query);
		while ($row = mysqli_fetch_assoc($data)) {
			array_push($mangcakhuc, new BaiHat($row['IdBaiHat'], $row['TenBaiHat'], $row['HinhBaiHat'], $row['CaSi'], $row['LinkBaiHat'], $row['LuotThich']));
		}
	}

	echo json_encode($mangcakhuc);
?>