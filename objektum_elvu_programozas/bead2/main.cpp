/**
 * Zalan Toth
 * Cz72ym
 * 2020.03.19
 * Fishing Competition (9.)
 */

#include <iostream>
#include "ContestEnumerator.h"
#include "AnglerEnumerator.h"

struct CarpStat {
    unsigned int count = 0;
    float weightMean = 0.0;
};


CarpStat carpAmountAndWeightMean(const std::string &file) {
    ContestEnumerator e(file);
    CarpStat carpStat={0,0.0};
    float sumOfWeights = 0.0;
    e.first();
    while (!e.end()) {
        ContestCompetitor competitor = e.current();
        carpStat.count += competitor.carpNumber;
        sumOfWeights += competitor.carpWeightSum;
        e.next();
    }
    if (carpStat.count > 0) {
        carpStat.weightMean = sumOfWeights / carpStat.count;
    }
    return carpStat;
}

unsigned int countOfAngerCatchOnlyCarp(const std::string &file) {
    AnglerEnumerator e(file);
    unsigned int count = 0;
    e.first();
    while (!e.end()) {
        Angler angler = e.current();
        if (angler.catchOnlyCarp) {
            count++;
        }
        e.next();
    }
    return count;
}

#define NORMAL_MODE
#ifdef NORMAL_MODE

int main() {
    std::string filename;
    std::cout << "Enter the name of the input file, please:";
    std::cin >> filename;

    CarpStat carpStat = carpAmountAndWeightMean(filename);
    std::cout << "Number of carp caught on competitions: "
              << carpStat.count << ", average weight: "
              << carpStat.weightMean << std::endl;

    int catchOnlyCarp = countOfAngerCatchOnlyCarp(filename);
    std::cout << "Number of angler caught only carp: " << catchOnlyCarp << std::endl;

    return 0;
}

#else
#define CATCH_CONFIG_MAIN

#include "catch.hpp"

TEST_CASE("countOfAngerCatchOnlyCarp No file should throw exception"){
    CHECK_THROWS(countOfAngerCatchOnlyCarp("nofile"));
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 0 on empty file","t1"){
    int result=countOfAngerCatchOnlyCarp("test/t1.txt");
    CHECK(result==0);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 1 if no fish was caught by an angler","t2"){
    int result=countOfAngerCatchOnlyCarp("test/t2.txt");
    CHECK(result==1);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 0 if no carp was caught by an angler","t3"){
    int result=countOfAngerCatchOnlyCarp("test/t3.txt");
    CHECK(result==0);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 1 if one carp was caught on one contest by an angler","t4"){
    int result=countOfAngerCatchOnlyCarp("test/t4.txt");
    CHECK(result==1);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 0 if one angler was caught only carps on the first contest but other on the second by an angler","t5"){
    int result=countOfAngerCatchOnlyCarp("test/t5.txt");
    CHECK(result==0);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 1 if one angler was caught only carps on the first contest but nothing on the second by an angler","t6"){
    int result=countOfAngerCatchOnlyCarp("test/t6.txt");
    CHECK(result==1);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 1 if first angler was caught only carps on all contests","t7"){
    int result=countOfAngerCatchOnlyCarp("test/t7.txt");
    CHECK(result==1);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 1 if lat angler was caught only carps on all contests","t14"){
    int result=countOfAngerCatchOnlyCarp("test/t14.txt");
    CHECK(result==1);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 2 if two angler were caught only carps","t8"){
    int result=countOfAngerCatchOnlyCarp("test/t8.txt");
    CHECK(result==2);
}

TEST_CASE("countOfAngerCatchOnlyCarp should return 0 if no one was caught any carp","t9"){
    int result=countOfAngerCatchOnlyCarp("test/t9.txt");
    CHECK(result==0);
}

TEST_CASE("carpAmountAndWeightMean No file should throw exception"){
    CHECK_THROWS(carpAmountAndWeightMean("nofile"));
}

TEST_CASE("carpAmountAndWeightMean should return 0 an 0.0 if no data in file","t1"){
    CarpStat result=carpAmountAndWeightMean("test/t1.txt");
    CHECK(result.count==0);
    CHECK(result.weightMean==0.0f);
}

TEST_CASE("carpAmountAndWeightMean should return 0 an 0.0 if no one was caught any fish","t10"){
    CarpStat result=carpAmountAndWeightMean("test/t10.txt");
    CHECK(result.count==0);
    CHECK(result.weightMean==0.0f);
}

TEST_CASE("carpAmountAndWeightMean should return 0 an 0.0 if no carps was caught","t11"){
    CarpStat result=carpAmountAndWeightMean("test/t11.txt");
    CHECK(result.count==0);
    CHECK(result.weightMean==0.0f);
}

TEST_CASE("carpAmountAndWeightMean should return 2 an 2.8 if 2 piece of carps was caught, 2.8 kg each","t12"){
    CarpStat result=carpAmountAndWeightMean("test/t12.txt");
    CHECK(result.count==2);
    CHECK(result.weightMean==2.8f);
}

TEST_CASE("carpAmountAndWeightMean should return 2 an 2.8 if 2 piece of carps was caught by last andler, 2.8 kg each","t14"){
    CarpStat result=carpAmountAndWeightMean("test/t14.txt");
    CHECK(result.count==2);
    CHECK(result.weightMean==2.8f);
}


TEST_CASE("carpAmountAndWeightMean should return only carps number and weight mean","t13"){
    CarpStat result=carpAmountAndWeightMean("test/t13.txt");
    CHECK(result.count==7);
    CHECK(result.weightMean==4.0f);
}

#endif