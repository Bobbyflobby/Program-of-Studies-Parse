# AHS Program of Studies Parse
By Walter Vincent <br />
Started Dec 2021 <br />

# How to use
Copy and paste Program of Studies into "input.txt" and run. <br />
Only works if course codes end in a "Z" (all ones I have seen do, have had to fix some missed ones). <br />
Outputs a json called "output.json" containing the course code, name, grades, credits, category, description, and level in json format <br />

# Config
Use "config.json" to change config settings <br />
debug (boolean) - true to enable debug information, false to hide <br />
courseKeys (String[String[2]]) -  course type comes from course code prefixes <br />
inputFileLocation (String) - location of input file, text file (from running dir)
outputFileLocation (String) - location of output file, json file (from running dir)


# Dependencies
openjdk-17
com.fasterxml.jackson.core:jackson-core:2.7.5 <br />
com.fasterxml.jackson.core:jackson-databind:2.7.5 <br />
