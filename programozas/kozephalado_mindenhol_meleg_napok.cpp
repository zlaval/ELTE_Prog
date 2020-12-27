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

    int K = 0;
    int result[1000];

    for (int i = 0; i < M; i++)
    {
        int j = 0;
        while (H[j][i] > 0 && j < N)
        {
            j++;
        }
        if (j >= N)
        {
            result[i] = 1;
            K++;
        }
        else
        {
            result[i] = 0;
        }
    }

    cout << K << " ";
    for (int i = 0; i < M; i++)
    {
        if (result[i] == 1)
        {
            cout << i + 1 << " ";
        }
    }

    return 0;
}
