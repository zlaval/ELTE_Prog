//6.1 NOK
#include <iostream>

using namespace std;

int H[1000][1000];

int main()
{
    int N, M;
    cin >> N >> M;

    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < M; j++)
        {
            int tmp;
            cin >> tmp;
            H[i][j] = tmp;
        }
    }

    float averages[1000];

    for (int i = 0; i < N; i++)
    {
        int sumTemp = 0;
        for (int j = 0; j < M; j++)
        {
            sumTemp += H[i][j];
        }
        averages[i] = (float)sumTemp / (float)M;
    }

    int maxIndex = -2;
    int maxTemp = -51;

    for (int i = 0; i < N; i++)
    {
        float actual = averages[i];
        if (actual > maxTemp)
        {
            maxTemp = actual;
            maxIndex = i;
        }
    }

    cout << maxIndex + 1;

    return 0;
}
