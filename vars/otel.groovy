def getLabels(String labels) {
	if(labels!=null && !labels.isEmpty()) {
		def pattern = ~".*-l\\s(.*)"
		def matcher = labels =~ pattern
		if(matcher.size() > 0) {
			return matcher[0][1].toString().replace("\\|", ",").trim();
		} else {
			return "noMatch"
		}

	} else {
		return "noLabel"
	}
}
