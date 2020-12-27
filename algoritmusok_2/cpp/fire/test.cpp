#include <set>

using namespace std;

using pair_type = std::pair<int, int>;

struct PairCmp
{
    bool operator()(const pair_type& lhs, const pair_type& rhs) const
    {
        return lhs.second < rhs.second;
    }
};

int main() {


    auto cmp = [](pair<int, int> a, pair<int, int> b) {
        return a == b || a.second < b.second;
    };

    std::set<pair_type, PairCmp> q;

    q.insert(make_pair(10, 100));
    q.insert(make_pair(20, 50));
    q.insert(make_pair(30, 150));
    q.insert(make_pair(40, 100));

    return 0;
}
