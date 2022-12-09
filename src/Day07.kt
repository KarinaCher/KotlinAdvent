fun main() {

    val MOST_SIZE = 100000
    val TOTAL_SPACE = 70000000
    val REQUIRED_FREE_SPACE = 30_000_000

    fun changeDir(currentDir: Dir, command: List<String>, rootDir: Dir): Dir {
        val dirName = command[2]
        return when (dirName) {
            "/" -> rootDir
            ".." -> currentDir.parent ?: rootDir
            else -> {

                if (!currentDir.dirs.map { it.name }.toList().contains(dirName)) {
                    val dir = Dir(name = dirName, currentDir)
                    currentDir.dirs.add(dir)
                }
                currentDir.dirs.filter { it.name.equals(dirName) }.first()
            }
        }
    }

    fun getFileStructure(input: List<String>): Dir {
        val rootDir = Dir(parent = null, name = "/")
        var currentDir = rootDir

        val iter = input.listIterator()

        var line = iter.next()
        while (iter.hasNext()) {

            if (line.startsWith("$")) { // command found
                val command = line.split(" ")
                if (command[1].equals("cd")) {
                    currentDir = changeDir(currentDir, command, rootDir)
                    line = iter.next()

                } else if (command[1].equals("ls")) {

                    var dirList = iter.next()
                    while (!dirList.startsWith("$")) {
                        val sp = dirList.split(" ")

                        if (dirList.startsWith("dir")) {
                            // add directory
                            val dirName = sp[1]
                            if (!currentDir.dirs.map { it.name }.toList().contains(dirName)) {
                                currentDir.dirs.add(Dir(name = dirName, parent = currentDir))
                            }
                        } else {
                            // add File
                            currentDir.files.add(ElvesFile(name = sp[1], size = sp[0].toInt()))
                        }
                        dirList = if (iter.hasNext()) iter.next() else break
                    }
                    line = dirList
                }
            }
        }
        return rootDir
    }

    fun dirSize(dir: Dir): Int {
        if (dir.files.isEmpty()) {
            return 0
        } else {
//            println("dir " + dir.name)
            return dir.files.stream()
//                .peek { println("file size " + it.name + " " +  it.size) }
                .mapToInt { it.size }.sum()
        }
    }

    fun traverse(parent: Dir, map: MutableMap<Dir, Int>): Int {

        for (dir in parent.dirs) {
            var result = 0

            if (dir.dirs.isNotEmpty()) {
                result += traverse(dir, map) + dirSize(dir)
            }
            else {
                result = dirSize(dir)
            }
            map.put(dir, result)
        }

        return parent.dirs.stream().mapToInt {map.get(it) ?: 0}.sum()
    }

    fun getList(root: Dir): Map<Dir, Int> {
        val map = mutableMapOf<Dir, Int>()
        val size = traverse(root, map) + dirSize(root)
        map.put(root, size)

        map.forEach { println("map: " + it.key.name + " " + it.value) }
        return map
    }

    fun part1(input: List<String>): Int {
        val root = getFileStructure(input)
        val traverse = getList(root)
        return traverse.filter { it.value < MOST_SIZE }.toMap().values.stream().mapToInt { it.toInt() }.sum()
    }

    fun part2(input: List<String>): Int {
        val root = getFileStructure(input)
        val list = getList(root)
        val sorted = list.toList().sortedBy { it.second }.toMap()

        val rootDir = list.filterKeys{ it.name.equals("/") }.values.first()

        var freeSpace = TOTAL_SPACE.minus(rootDir)
        for (dir in sorted) {
            val dirSize = dir.value
            println("free " + freeSpace + " " + dirSize + " sum " + (freeSpace.plus(dirSize)))
            if ((freeSpace.plus(dirSize)) > REQUIRED_FREE_SPACE) {
                return dirSize
            }

        }

        return -1
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

class Dir(
    val name: String,
    val parent: Dir?,
    val files: MutableList<ElvesFile> = mutableListOf(),
    val dirs: MutableList<Dir> = mutableListOf()
)

class ElvesFile(
    val name: String,
    val size: Int
)