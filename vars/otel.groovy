
@NonCPS
def checkLabels(String text, String appliance) {
	def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-TAG/
	if(appliance ==~ pattern){
		def (res1) = appliance =~ pattern
		def list1 = getLabels(text, getLj(res1[1]))
		def list2 = getLabels(text, getLj(res1[2]))
		def createdList = list1 + list2
		return createdList
	}
	else {
		return getLabels(text, appliance)
	}
}


def getLabels(String str, String rig) {
	println rig
	// def pattern = /.*\s-l\s(.*)/
	// def lblList
	// if(text2 ==~ pattern) {
	// 	def (word1) = text2 =~ pattern
	// 	lblList = word1[1].toString().replaceAll(/\\\|/, ",").trim()
	// } else {
	// 	return "noMatch"
	// }
	// def res = sh(script:"""
	// 						curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=${appliance2}' \
	// 						--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
	// 						jq -r -j '.objects[] | .tags + \",\" + .generation.name'
	// 					""", returnStdout: true, label: "xpool_allocation")

	// ArrayList ljLabels = res.split(",")
	// ArrayList listlabes = lblList.split(",")
    // return listlabes.intersect(ljLabels).join(", ")
	return "klj,lkj"
}

@NonCPS
def getLj(String machine) {
	def res =  steps.sh(script:"""
							curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=${machine}' \
							--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
							jq -r -j '.objects[] | .tags + \",\" + .generation.name'
						""", returnStdout: true, label: "xpool_allocation")
}
