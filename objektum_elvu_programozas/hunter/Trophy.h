
#ifndef OOP_TROPHY_H
#define OOP_TROPHY_H

class Trophy {
protected:
    std::string placeOfCapture;
    std::string dateOfCapture;
    int weight;
public:
    Trophy(const std::string &placeOfCapture, const std::string &dateOfCapture, int weight) {
        this->placeOfCapture = placeOfCapture;
        this->dateOfCapture = dateOfCapture;
        this->weight = weight;
    }

    virtual ~Trophy() {}
    virtual int getSpecialAttrib() const = 0;
    std::string getPlaceOfCapture() const { return placeOfCapture; }
    std::string getDateOfCapture() const { return dateOfCapture; }
    int getWeight() const { return weight; }
};

class Lion : public Trophy {
public:
    enum GENDER {
        MALE, FEMALE
    };
    Lion(const std::string &placeOfCapture, const std::string &dateOfCapture, int weight, GENDER gender)
        : Trophy(placeOfCapture, dateOfCapture, weight) {
        this->gender = gender;
    }
    int getSpecialAttrib() const override { return gender; }
private:
    GENDER gender;
};

class Elephant : public Trophy {
private :
    int lengthOfTusk;
public:
    Elephant(const std::string &placeOfCapture, const std::string &dateOfCapture, int weight, int lengthOfTusk)
        : Trophy(placeOfCapture, dateOfCapture, weight) {
        this->lengthOfTusk = lengthOfTusk;
    }
    int getSpecialAttrib() const override { return lengthOfTusk; }
};

class Rhino : public Trophy {
private:
    int weightOfHorn;
public:
    Rhino(const std::string &placeOfCapture, const std::string &dateOfCapture, int weight, int weightOfHorn)
        : Trophy(placeOfCapture, dateOfCapture, weight) {
        this->weightOfHorn = weightOfHorn;
    }
    int getSpecialAttrib() const override { return weightOfHorn; }
};

#endif //OOP_TROPHY_H
