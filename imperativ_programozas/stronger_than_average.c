#include <stdio.h>
#include <stdlib.h>

struct Result
{
    int id;
    int points;
};

struct Competition
{
    float average;
    int numberOfRunners;
    int minResult;
    struct Result *results;
};

int main()
{

    int numberOfStudents;
    int numberOfCompetitions;

    scanf("%d %d", &numberOfStudents, &numberOfCompetitions);

    struct Competition competitions[numberOfCompetitions];

    for (int i = 0; i < numberOfCompetitions; i++)
    {
        scanf("%d", &competitions[i].minResult);
    }

    float fullAverageSum = 0.0;
    for (int i = 0; i < numberOfCompetitions; i++)
    {
        int sumOfPoints = 0;
        int numberOfRunners;
        scanf("%d", &numberOfRunners);
        competitions[i].numberOfRunners = numberOfRunners;
        competitions[i].results = (struct Result *)calloc(numberOfRunners, sizeof(struct Result));

        for (int j = 0; j < numberOfRunners; j++)
        {
            scanf("%d %d", &competitions[i].results[j].id, &competitions[i].results[j].points);
            sumOfPoints += competitions[i].results[j].points;
        }
        competitions[i].average = (float)sumOfPoints / numberOfRunners;
        fullAverageSum += competitions[i].average;
    }

    float fullAverage = fullAverageSum / numberOfCompetitions;

    int competitionAboveAverage = 0;
    int competitionId[numberOfCompetitions];
    int k = 0;
    for (int i = 0; i < numberOfCompetitions; i++)
    {
        if (fullAverage < competitions[i].average)
        {
            competitionAboveAverage++;
            competitionId[k++] = i + 1;
        }
    }

    printf("%d ", competitionAboveAverage);
    for (int i = 0; i < competitionAboveAverage; i++)
    {
        printf("%d ", competitionId[i]);
    }

    for (int i = 0; i < numberOfCompetitions; i++)
    {
        free(competitions[i].results);
    }

    return 0;
}