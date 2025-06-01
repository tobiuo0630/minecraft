package mee.example.untitled1

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

object Mazegene {
    private const val WALL_HEIGHT=2

    fun generateMaze(start: Location, width:Int, height:Int, world: World){//迷路の２次元配列作成－＞DFSで道を掘る－＞ブロックを実際に配置
        val mazeWidth = width * 2+1
        val mazeHeight = height * 2+1
        val visited = Array(height){BooleanArray(width)}
        val maze =Array(mazeHeight){IntArray(mazeWidth){ 1 } }

        maze[1][1] = 0
        generateDFS(maze, visited,0,0)

        for(y in 0 until mazeHeight){
            for(x in 0 until mazeWidth){
                val blockLoc = start.clone().add(x.toDouble(),0.0,y.toDouble())
                val type = if(maze[y][x]==1) Material.STONE else Material.AIR
                for(h in 0..WALL_HEIGHT){
                    blockLoc.clone().add(0.0,h.toDouble(),0.0).block.type=type
                }
            }
        }
    }

    private fun generateDFS(//再帰的に迷路の道路を掘っていく処理（深さ優先探索）
        maze: Array<IntArray>,
        visited:Array<BooleanArray>,
        cx: Int,
        cy: Int
    ){
        visited[cy][cx] = true
        val dirs = listOf(0 to -1,1 to 0,0 to 1,-1 to 0).shuffled()

        for((dx,dy) in dirs){
            val nx = cx+dx
            val ny = cy+dy
            if(nx in visited[0].indices && ny in visited.indices && !visited[ny][nx]){
                maze[cy * 2 +1 +dy][cx * 2 + 1 + dx] = 0
                maze[ny * 2 + 1][nx * 2+1]=0
                generateDFS(maze,visited,nx,ny)
            }
        }
    }
}