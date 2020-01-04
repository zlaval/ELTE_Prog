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
    int maxTemps[1000];

    for (int i = 0; i < N; i++)
    {
        int max = -51;
        int sumTemp = 0;
        for (int j = 0; j < M; j++)
        {
            int actual = H[i][j];
            sumTemp += actual;
            if (max < actual)
            {
                max = actual;
            }
        }
        int average =(float) sumTemp / (float)M;

        maxTemps[i] = max;
        averages[i] = average;
    }

    int result = -2;
    bool found = false;
    int i = 0;
    while (!found && i < N)
    {
        int j = 0;

        int actual = maxTemps[i];
        while (!found && j < N)
        {
            if (i != j)
            {
                float average = averages[j];
                if (actual < average)
                {
                    result = i;
                    found = true;
                }
            }
            j++;
        }

        i++;
    }

    cout << result + 1;

    return 0;
}
