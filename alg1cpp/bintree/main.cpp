#include "BinTreeAlgs.h"
#include "BinTreeBuilder.h"
#include "SearchTreeBuilder.h"
#include "BinTreeVisualizer.h"
#include "SearchBinTreeAlgs.h"


int main() {
    BinTreeBuilder binTreeBuilder;
    SearchTreeBuilder searchTreeBuilder;
    BinTreeVisualizer binTreeVisualizer;
    BinTreeAlgs algs;
    SearchBinTreeAlgs searchBinTreeAlgs;
//
//    Node *node1 = binTreeBuilder.buildFromParenthesesExpression("(((17((15(13))16((12)14)))19)20((5)18((8(6))10(7))))");
//    Node *node2 = binTreeBuilder.buildFromParenthesesExpression(
//        "(((((13)4(1))3)11((12)9(8)))5((10((16)7((15)14(6))))2))");
//
//    std::cout << "Preorder: ";
//    algs.preOrder(node2);
//    std::cout << std::endl;
//
//    std::cout << "Inorder: ";
//    algs.inOrder(node2);
//    std::cout << std::endl;
//
//    std::cout << "Postorder: ";
//    algs.postOrder(node2);
//    std::cout << std::endl;
//
//    int height = algs.treeHeight(node2);
//    std::cout << height << std::endl;
//
//    Node *node3 = binTreeBuilder.buildFromPreOrderTraversal("D E M L A H P B K G N Q C F",
//                                                            "L M H A E D B G K N P C F Q");
//    Node *node4 = binTreeBuilder.buildFromPostOrderTraversal("13 1 4 3 12 8 9 11 16 15 6 14 7 10 2 5",
//                                                             "13 4 1 3 11 12 9 8 5 10 16 7 15 14 6 2");
//
//    binTreeVisualizer.printTree(node4);
//    std::cout << std::endl;
//    std::cout << "LevelOrder: ";
//    algs.levelOrder(node4);
//
//    std::cout << "Leaf count: " << algs.leafCount(node4) << std::endl;
//
//    Node *node5 = searchTreeBuilder.buildSearchTreePreOrder("2 1 6 3 5 8 7 9");
//    binTreeVisualizer.printTree(node5);
//    std::cout << std::endl;
//    std::cout << "Teszt" << std::endl;
//    Node *node6 = searchTreeBuilder.buildSearchTreePreOrder("8 4 3 5 7 15 14 13 17 16 20 18");
//    binTreeVisualizer.printTree(node6);
//    std::cout << std::endl;
//    Node *founded = searchBinTreeAlgs.search(node6, "15");
//    if(founded!= nullptr) {
//        std::cout<<"Key found: " << founded->getKey() << std::endl;
//    }else{
//        std::cout <<"Not in the search tree"<< std::endl;
//    }
//    delete node1;
//    delete node2;
//    delete node3;
//    delete node4;
//    delete node5;
//    delete node6;


    Node *node4 = binTreeBuilder.buildFromPostOrderTraversal("D G H N Z C M T B A I E",
                                                             "G D Z H N M C E T I A B");

    binTreeVisualizer.printTree(node4);
    std::cout<< std::endl;
    algs.preOrder(node4);


    std::getchar();
    return 0;
}