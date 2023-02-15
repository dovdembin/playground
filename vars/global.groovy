def call(Map config = [:] ) {
    sh "echo custom hello ${config.name} today is ${config.dayOfWeek}."
}