def countTestOTel() {
    GroovyShell shell = new GroovyShell()
def tools = shell.parse(new File('ExampleConfiguration.groovy'))
tools.greet()  
}

try {
 countTestOTel()
} catch (Exception e) {
         
    }


