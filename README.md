# VulcanDashboard v0.0.1
currently in development, no beta releases. 

## Building this project
if you want to help me work on this project, here are some instructions--
<br>

this project runs on maven, and has all of the javafx dependencies built in(hopefully). first things first, clone this repo and open it up in intellij. if you get an error, follow the context action or google it.
<br>

when you first clone the project, it will have no default build configuration. go to the top right corner of intellij, click add Configuration... and add a maven configuration. in the command line box, add `javafx:run` and then you're all set!

# Some common errors when building this project are--

## lambda expressions not supported at language level '5'
go to `File > Project Structure > Modules > VulcanDash` and change the language level to 11 or something high that supports lambda expressions, because i use a lot of lambda expressions for event listeners. make sure to click apply after making the change. 
<br>

You may also have to go into `File > Build, Execution, Deployment > Java Compiler` and delete the bytecode version so it defaults to same as project language level, then click apply. 
## Maven not importing dependencies properly
if javafx seems to be giving a bunch of red lines all over the place, chances are your maven didn't import the packages yet. just reimport all dependencies on maven and it should work itself out. 

