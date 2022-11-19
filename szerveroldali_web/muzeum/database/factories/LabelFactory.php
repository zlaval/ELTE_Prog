<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Str;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Label>
 */
class LabelFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            'name' => Str::ucfirst(implode(' ', fake()->words(rand(1, 2)))),
            'display' => fake()->boolean(75),
            'color' => fake()->hexColor() . 'ff',
        ];
    }
}
