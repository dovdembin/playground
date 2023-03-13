def getLabels(String text) {
	def pattern = /.*\s-l\s(.*)/
	if(text ==~ pattern) {
		def (word1) = text =~ pattern
		def lblList = word1[1].toString().replaceAll(/\\\|/, ",").trim()
		return getIntersection(lblList)
	} else {
		return "noMatch"
	}
	 
}

ArrayList<String> getIntersection(String labels){
	ArrayList listlabes = labels.split(",")
	// println listlabes
	def res = sh(script: """
	curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=WK-D0046' \
	--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
	jq -r -j '.objects[] | .tags + "," + .generation.name'
	""", returnStdout: true, label: "xpool_allocation")
	println res			
	// ArrayList ljLabels = res.split(",")
	// return listlabes.intersect(ljLabels)
	return listlabes
}

// config_params       = ['xpoolAllocation':'--lg 1 -l indus1\\|indus2\\|indus3,PhysicalLG,powerActionSupported,@Indus_Not_Aligned']

// println getLabels(config_params['xpoolAllocation'])