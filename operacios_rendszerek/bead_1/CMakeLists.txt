# cmake_minimum_required(VERSION <specify CMake version here>)
project(bead_1 C)

set(CMAKE_C_STANDARD 11)

add_executable(bead_1 main.c patient.h file_handler.h add_processor.h add_processor.c file_handler.c delete_processor.h delete_processor.c modify_processor.h modify_processor.c utils.c utils.h vaccination_processor.c vaccination_processor.h mock_generator.c mock_generator.h gyak/signal.c gyak/message_queue.c gyak/semaphore.c gyak/example_zh.c gyak/shared_memory.c)
add_executable(signal gyak/signal.c)
add_executable(mq gyak/message_queue.c)
add_executable(zh_example gyak/example_zh.c)
add_executable(sm gyak/shared_memory.c)
add_executable(sema  gyak/semaphore.c)
add_executable(mq2  gyak/mq2.c)


add_executable(zh_task_1 zh/1.c)
add_executable(zh_task_2 zh/2.c)
add_executable(zh_task_4 zh/4.c)