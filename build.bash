# this script is called when the judge is building your compiler.
# no argument will be passed in.

cd "$(dirname "$0")"
mkdir -p bin
find ./src/ -name *.java | javac -d bin -cp "/ulib/java/antlr-4.8-complete.jar" @/dev/stdin
