#include "Menu.h"

using namespace std;

#define NORMAL_MODE
#ifdef NORMAL_MODE

int main() {
    Menu menu;
    menu.run();
    return 0;
}

#else

#define  CATCH_CONFIG_MAIN

#include "catch.hpp"
#include "bag.h"

TEST_CASE("New Bag should contains zero single elements", "[getSingleCardinalityElementsCount]") {
    //given
    Bag bag;

    //when
    int singleCardinalityCount = bag.getSingleCardinalityElementsCount();

    //then
    CHECK(singleCardinalityCount == 0);
}

TEST_CASE("Add should increase single elements count", "[getSingleCardinalityElementsCount,put]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    int singleCardinalityCount = bag.getSingleCardinalityElementsCount();
    //then
    CHECK(singleCardinalityCount == 1);
}

TEST_CASE("Add element twice should decrease single elements count", "[getSingleCardinalityElementsCount,put]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    int singleCardinalityCountOnFirstAdd = bag.getSingleCardinalityElementsCount();
    bag.put(3);
    int singleCardinalityCountOnSecondAdd = bag.getSingleCardinalityElementsCount();
    //then
    CHECK(singleCardinalityCountOnFirstAdd == 1);
    CHECK(singleCardinalityCountOnSecondAdd == 0);
}

TEST_CASE("Remove on singly occurred element should decrease single elements count",
          "[getSingleCardinalityElementsCount,put,remove]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    int singleCardinalityCountOnAdd = bag.getSingleCardinalityElementsCount();
    bag.remove(3);
    int singleCardinalityCountOnRemove = bag.getSingleCardinalityElementsCount();
    //then
    CHECK(singleCardinalityCountOnAdd == 1);
    CHECK(singleCardinalityCountOnRemove == 0);
}

TEST_CASE("Add individual elements should increase single elements cardinality to elements count",
          "[getSingleCardinalityElementsCount,put]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    bag.put(5);
    bag.put(7);
    bag.put(8);
    bag.put(9);
    bag.put(9);
    int singleCardinalityCount = bag.getSingleCardinalityElementsCount();

    //then
    CHECK(singleCardinalityCount == 4);
}

TEST_CASE("Remove two from element with amount three should increase cardinality to one",
          "[getSingleCardinalityElementsCount,put,remove]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    bag.put(3);
    bag.put(3);
    int singleCardinalityCountAfterAdd = bag.getSingleCardinalityElementsCount();
    bag.remove(3, 2);
    int singleCardinalityCountAfterRemove = bag.getSingleCardinalityElementsCount();
    //then
    CHECK(singleCardinalityCountAfterAdd == 0);
    CHECK(singleCardinalityCountAfterRemove == 1);
}

TEST_CASE("Remove two from element with amount four should not touch cardinality",
          "[getSingleCardinalityElementsCount,put,remove]") {
    //given
    Bag bag;
    //when
    bag.put(3);
    bag.put(3);
    bag.put(3);
    bag.put(3);
    int singleCardinalityCountAfterAdd = bag.getSingleCardinalityElementsCount();
    bag.remove(3, 2);
    int singleCardinalityCountAfterRemove = bag.getSingleCardinalityElementsCount();
    //then
    CHECK(singleCardinalityCountAfterAdd == 0);
    CHECK(singleCardinalityCountAfterRemove == 0);
}

TEST_CASE("Add elements hundred times should have count 100", "[getCount,put]") {
    //given
    Bag bag;

    //when
    for (int i = 0; i < 100; i++) {
        bag.put(7);
    }
    int count = bag.getCount(7);
    //then
    CHECK(count == 100);
}

TEST_CASE("Remove element with amount greater then element count should throw INVALID_REMOVE_AMOUNT exception",
          "[put,remove]") {
    //given
    Bag bag;

    //when
    bag.put(1);
    bag.put(1);

    //then
    CHECK_THROWS(bag.remove(1, 3));
}

TEST_CASE("When remove zero amount from the only element with single occurs, cardinality count should stay one",
          "[put,remove]") {
    //given
    Bag bag;

    //when
    bag.put(3);
    int cardinalityAfterPut = bag.getSingleCardinalityElementsCount();
    bag.remove(3, 0);
    int cardinalityAfterRemove = bag.getSingleCardinalityElementsCount();

    //then
    CHECK(cardinalityAfterPut == 1);
    CHECK(cardinalityAfterRemove == 1);
}

TEST_CASE("When remove 1 amount from the only element with single occurs, bag size should be 0",
          "[put,remove]") {
    //given
    Bag bag;

    //when
    bag.put(3);
    bag.remove(3);

    //then
    CHECK(bag.size() == 0);
}

#endif
