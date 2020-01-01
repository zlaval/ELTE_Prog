#include <iostream>

using namespace std;

int H[1000][1000];

int main()
{

    int N, M;

    cin >> N >> M;

    int maxTemp = -51;

    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < M; j++)
        {
            int tmp;
            cin >> tmp;
            H[i][j] = tmp;
            if (maxTemp < tmp)
            {
                maxTemp = tmp;
            }
        }
    }

    int result[1000];
    for (int i = 0; i < 1000; i++)
    {
        result[i] = 0;
    }

    int k=0;
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < M; j++)
        {
            if (H[i][j] == maxTemp)
            {
                result[i] = 1;
                k++;
                break;
            }
        }
    }

    cout << k << " ";
    for (int i = 0; i < N; i++)
    {
        if (result[i] == 1)
        {
            cout << i + 1 << " ";
        }
    }

    return 0;
}
