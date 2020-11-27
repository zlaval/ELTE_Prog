cmake_minimum_required(VERSION 3.15)
project(alg1cpp)

set(CMAKE_CXX_STANDARD 14)

add_executable(zh1 zh1/main.cpp zh1/FileReader.cpp zh1/ListHandler.cpp)
add_executable(radix radix/main.cpp)
add_executable(heap heap/main.cpp)
add_executable(hashing hashing/main.cpp)
add_executable(bintree bintree/main.cpp)
add_executable(bead6 bead6/main.cpp)
add_executable(fw algo2/floydwarshall/main.cpp)
add_executable(warshall algo2/warshall/main.cpp)
add_executable(flight algo2/sortest_flight/main.cpp)
