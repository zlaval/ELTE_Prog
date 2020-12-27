#include <iostream>

using namespace std;

int main()
{

    int dayNumber;
    cin >> dayNumber;

    int sumOfFrozenDays=0;

    int highestSub=0;
    int highestSubIndex=0;

    int maxLowerThenPrevMinIndex= -1;

    int prevMin;

    int numberOfPosMin=0;
    int indexMinPos[dayNumber];

    for(int i=0;i<dayNumber;i++){
        int dailyMin;
        int dailyMax;
        cin >> dailyMin;
        cin >> dailyMax;

        if(dailyMin<=0){
            sumOfFrozenDays++;
        }
        if(dailyMin<=0 && dailyMax>=0){
            indexMinPos[ numberOfPosMin++]=i+1;
        }

        int dailySub=dailyMax - dailyMin;

        if(dailySub>highestSub){
            highestSub=dailySub;
            highestSubIndex=i+1;
        }
        if(i!=0 && maxLowerThenPrevMinIndex==-1){
            if(prevMin>dailyMax){
                maxLowerThenPrevMinIndex=i+1;
            }
        }
        prevMin= dailyMin;
    }


    cout<<"#"<<endl;
    cout<<sumOfFrozenDays<<endl;
    cout<<"#"<<endl;
    cout<<highestSubIndex<<endl;
    cout<<"#"<<endl;
    cout<<maxLowerThenPrevMinIndex<<endl;
    cout<<"#"<<endl;
    cout<<numberOfPosMin<<" ";

    for(int i=0;i<numberOfPosMin;i++){
        cout<<indexMinPos[i]<<" ";
    }

    return 0;
}
