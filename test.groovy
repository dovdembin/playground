def countTestOTel() {
    GroovyShell shell = new GroovyShell()
def tools = shell.parse(new File('function_tools.gvy'))
tools.greet()  
}

try {
 countTestOTel()
} catch (Exception e) {
         
    }


