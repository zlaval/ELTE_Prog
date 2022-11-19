<x-guest-layout>
    <div class="container mx-auto p-3 overflow-hidden min-h-screen">
        <div class="mb-5">
            <h1 class="font-semibold text-3xl mb-4">{{ $label -> name }} szerkesztése</h1>
            <a href="{{ route('home') }}" class="text-blue-400 hover:text-blue-600 hover:underline"><i class="fas fa-long-arrow-alt-left"></i> Vissza a főoldalra</a>
        </div>

        <div class="flex justify-start my-5">
            <form method="POST" action="{{ route('labels.destroy', $label) }}" id="delete-label"
                  class="bg-red-500 hover:bg-red-700 px-2 mx-2 py-2 rounded text-white "
            >
                @csrf
                @method('DELETE')
                <a href=""
                   onclick="event.preventDefault(); document.querySelector('#delete-label').submit(); "
                ><i class="fa fa-trash p-1 "></i>Töröl</a>
            </form>
        </div>

        <form
            x-data="{ displayName: '{{ old('name', $label -> name) }}', bgColor: '{{ old('color', $label -> color)}}', display: '{{old('display'),$label->display  }}'}"
            x-init="() => {
            new Picker({
                color: bgColor,
                popup: 'bottom',
                parent: $refs.bgColorPicker,
                onDone: (color) => bgColor = color.hex
            });
        }"
            action="{{ route('labels.update', $label) }}"
            method="POST">
            @csrf
            @method('PATCH')
            <div class="grid grid-cols-4 gap-6">
                <div class="col-span-4 lg:col-span-2 grid grid-cols-2 gap-3">
                    <div class="col-span-2">
                        <label for="name" class="block font-medium text-gray-700">Címke neve</label>
                        <input
                            type="text"
                            name="name"
                            id="name"
                            class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300" x-model="displayName">
                        @error('name')
                        <div class="font-medium text-red-500">{{ $message }}</div>
                        @enderror
                    </div>
                    <div class="col-span-2">
                        <label class="block text-sm font-medium text-gray-700">Háttér színe</label>
                        <div x-ref="bgColorPicker" id="bg-color-picker" class="mt-1 h-8 w-full border border-black" :style="`background-color: ${bgColor};`"></div>
                        <p x-text="bgColor"></p>
                        @error('color')
                        <div class="font-medium text-red-500">{{ $message }}</div>
                        @enderror
                    </div>
                    <div class="col-span-2">
                        <label class="block text-sm font-medium text-gray-700">Látható</label>
                        <input
                            id="display"
                            type="checkbox"
                            name="display"
                            value="1"
                            @if($label -> display) checked @endif
                            class="focus:ring-blue-500 focus:border-blue-500 block shadow-sm border-gray-300"
                            x-model="display">
                        @error('display')
                        <div class="font-medium text-red-500">{{ $message }}</div>
                        @enderror
                    </div>
                </div>
                <div class="col-span-4 lg:col-span-2">
                    <div x-show="displayName.length > 0">
                        <label class="block font-medium text-gray-700 mb-1">Előnézet</label>
                        <span x-text="displayName" :style="`background-color: ${bgColor};`" class="py-0.5 px-1.5 font-semibold"></span>
                    </div>
                </div>
            </div>

            <input type="hidden" id="color" name="color" x-model="bgColor" />

            <button type="submit" class="mt-6 bg-blue-500 hover:bg-blue-600 text-gray-100 font-semibold px-2 py-1 text-xl">Mentés</button>
        </form>
    </div>
</x-guest-layout>
