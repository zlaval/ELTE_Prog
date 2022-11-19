<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Item>
 */
class ItemFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            'name' => Str::ucfirst(implode(' ', fake()->words(rand(1, 3)))),
            'description' => fake()->text(200),
            'obtained' => fake()->date(),
            'image' => fake()->image(null,640,480),
        ];
    }
}
