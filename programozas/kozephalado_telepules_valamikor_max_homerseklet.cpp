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

    int maxOnEachDay[1000];

    for (int j = 0; j < M; j++)
    {
        int maxTemp = -51;
        for (int i = 0; i < N; i++)
        {
            if (maxTemp < H[i][j])
            {
                maxTemp = H[i][j];
            }
        }
        maxOnEachDay[j] = maxTemp;
    }

    int result[1000];
    for (int i = 0; i < 1000; i++)
    {
        result[i] = 0;
    }

    for (int j = 0; j < M; j++)
    {
        int maxTemp = maxOnEachDay[j];
        for (int i = 0; i < N; i++)
        {
            if (maxTemp == H[i][j])
            {
                result[i] = 1;
            }
        }
    }

    int K = 0;
    for (int i = 0; i < N; i++)
    {
        K += result[i];
    }

    cout << K << " ";
    for (int i = 0; i < N; i++)
    {
        if (result[i] == 1)
        {
            cout << i + 1 << " ";
        }
    }

    return 0;
}
