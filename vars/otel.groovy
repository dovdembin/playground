def getLabels(String text) {
	def textorigin = "EX,MLK,PhysicalLG"
	ArrayList origin = textorigin.split(",")
	// cmd ="curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=WK-D0115' --header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | jq '.objects[].tags'"
	
	res = sh(script: """
						curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=WK-D0046' \
						--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
						jq -r '.objects[] | .tags + "," + .generation.name'
					""", returnStdout: true, label: "xpool_allocation")
	
	ArrayList arrList = res.split(",")
	println origin
	println arrList
	println origin.intersect(arrList)
}


// config_params       = ['xpoolAllocation':'--lg 1 -l indus1\\|indus2\\|indus3,PhysicalLG,powerActionSupported,@Indus_Not_Aligned']

// println getLabels(config_params['xpoolAllocation'])