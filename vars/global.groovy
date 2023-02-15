def method1(Map config = [:] ) {
    sh "echo custom hello ${config.name} today is ${config.dayOfWeek}."
}

def enother()(Map config = [:] ) {
    sh "echo custom hello ${config.name} today is ${config.dayOfWeek}."
}