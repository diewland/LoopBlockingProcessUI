# LoopBlockingProcessUI
Loop blocking process in Android without UI freeze

1. create heavy process by suspend function and loop inside GlobalScope to keep order
2. run heavy process inside Handler to make screen not freeze when update UI
