def getLabels(String labels) {
	if(labels!=null && !labels.isEmpty()) {
		def pattern = ~".*-l(.*)"
		def matcher = labels =~ pattern
		return matcher[0][1].toString().replace("\\|", ",").trim();
	} else {
		return "noLabel"
	}
}