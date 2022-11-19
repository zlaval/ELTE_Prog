<?php

namespace Database\Seeders;

// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Item;
use App\Models\Label;
use App\Models\Comment;


class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {

        User::factory()->create([
            'email' => 'admin@szerveroldali.hu',
            'is_admin' => true,
            'password' => '$2y$10$cP1/XFrNrDLbwiJzrOxyzOA26aBOy0TKPFtCl7SHtlAAxpVMTAQ..' //adminpwd
        ]);

        $users = collect();
        for ($i = 1; $i <= 5; $i++) {
            $users->add(User::factory()->create([
                'email' => 'user' . $i . '@szerveroldali.hu'
            ]));
        }

        $labels = Label::factory(5)->create();
        $itemNames = collect(['Mona Lisa', 'T-Rex', 'Tutanhamon maszkja', 'David']);
        $itemLinks = collect(['monalisa.jpg', 'trex.jpg', 'tutanhamon_mask.jpg', 'david.jpg']);
        $items = collect();
        for ($i = 0; $i < 4; $i++) {
            $items->add(Item::factory()->create([
                'name' => $itemNames[$i],
                'image' => $itemLinks[$i]
            ]));
        }

        $items->each(function ($item) use ($labels) {
            $item->save();
            $item->labels()->attach($labels->random(rand(1, $labels->count())));
        });


        $comments = Comment::factory(12)->create([
            'user_id' => $users->random()->id,
            'item_id' => $items->random()->id
        ]);

        $comments->each(function ($comment) use (&$users, &$items) {
            $comment->user()->associate($users->random())->save();
            $comment->item()->associate($items->random())->save();
        });

    }
}
