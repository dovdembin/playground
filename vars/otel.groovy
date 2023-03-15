
def checkLabels(String text, String appliance) {
	def pattern = /([A-Z][A-Z]-[A-Z]\d\d\d\d)-([A-Z][A-Z]-[A-Z]\d\d\d\d)-.*/
	def m1
	def m2
	if(appliance ==~ pattern){
		def (res1) = appliance =~ pattern
		m1 = res1[1]
		m2 = res1[2]
	}
	else {
		return getLabels(text, appliance)
	}
	ArrayList list1 = getLabels(text, m1).split(",")
	ArrayList list2 = getLabels(text, m2).split(",")
	def createdList = list1 + list2
	return createdList.unique().join(",")
}


def getLabels(String str, String rig) {
	def pattern = /.*-l\s(.*)/
	def lblList
	if(str ==~ pattern) {
		def (word1) = str =~ pattern
		lblList = word1[1].toString().replaceAll(/\\\|/, ",").trim()
	} else {
		return "noMatch"
	}
	def res = sh(script:"""
							curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=${rig}' \
							--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
							jq -r -j '.objects[] | .tags + \",\" + .generation.name'
						""", returnStdout: true, label: "xpool_allocation")
	ArrayList ljLabels = res.split(",")
	ArrayList listlabes = lblList.split(",")
	def generation = sh(script:"""
							curl -s --location 'http://labjungle.devops.xiodrm.lab.emc.com/api/v1/cluster/?name=${rig}' \
							--header 'Authorization: ApiKey cute:9703aa016d613b2b21bbb0e6833c3078c811a5d1' | \
							jq '.objects[].generation.name'
						""", returnStdout: true, label: "xpool_allocation")
	println generation
    return listlabes.intersect(ljLabels).add(generation)
}
