#include <iostream>
#include "ListHandler.h"

void process(Node *storage, Node *delivery, ListHandler &handler);

void print(std::string text, const Node *list);

int main() {
    std::string storageFile;
    std::string deliveryFile;

    std::cout << "Enter the path of the storage file:";
    std::cin >> storageFile;
    std::cout << "Enter the path of the delivery file:";
    std::cin >> deliveryFile;

    FileReader storageReader(storageFile);
    FileReader deliveryReader(deliveryFile);

    ListHandler listHandler;
    Node *FEJ = listHandler.read(&storageReader);
    Node *FEJM = listHandler.read(&deliveryReader);

    process(FEJ, FEJM, listHandler);

    std::cout<<"***********************************************"<<std::endl;
    print("Storage quantities after served the order:", FEJ);
    std::cout<<"-----------------------------------------------"<<std::endl;
    print("Not served orders:", FEJM);
    std::cout<<"***********************************************"<<std::endl;
    return 0;
}

void process(Node *FEJ, Node *FEJM, ListHandler &handler) {
    Node *storage = FEJ->next;
    Node *prevDelivery = FEJM;
    Node *delivery = FEJM->next;

    while (storage != nullptr && delivery != nullptr) {
        Product *item = storage->product;
        Product *order = delivery->product;
        if (item->id == order->id) {
            if (item->quantity >= order->quantity) {
                item->quantity -= order->quantity;
                delivery = delivery->next;
                Node *removed = handler.outNext(prevDelivery);
                delete removed;
            } else {
                prevDelivery = delivery;
                delivery = delivery->next;
                std::cout << "Not enough product to serve the order from id: " << storage->product->id << std::endl;
            }
            storage = storage->next;
        } else if (item->id < order->id) {
            storage = storage->next;
        } else if (item->id > order->id) {
            std::cout << "No product in the storage with id: " << delivery->product->id << std::endl;
            prevDelivery = delivery;
            delivery = delivery->next;
        }
    }
    while (delivery!=nullptr){
        std::cout << "No product in the storage with id: " << delivery->product->id << std::endl;
        delivery = delivery->next;
    }

}

void print(std::string text, const Node *list) {
    Node *data = list->next;
    std::cout << text << std::endl;
    while (data != nullptr) {
        std::cout << data->product->id << ". = " << data->product->quantity << std::endl;
        data = data->next;
    }
    std::cout << std::endl;
}
