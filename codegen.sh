set -e
cd "$(dirname "$0")"
export CCHK="java -classpath ./lib/antlr-4.8-complete.jar:./bin Compiler.Compiler"
cat > code.mx
$CCHK