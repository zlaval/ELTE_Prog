<x-guest-layout>
    <div class="container mx-auto p-3 lg:px-36">

        <div class="grid grid-cols-1 lg:grid-cols-2 mb-4">
            <div>
                <h1 class="font-bold my-4 text-4xl">{{ $label -> name }} tárgyak</h1>
                <a href="{{ route('home') }}" class="text-blue-400 hover:text-blue-600 hover:underline"><i class="fas fa-long-arrow-alt-left"></i> Vissza a főoldalra</a>
            </div>
        </div>
        <div class="grid grid-cols-3 gap-6">
            <div class="col-span-3 lg:col-span-3">
                <div class="grid grid-cols-3 gap-3">
                    @foreach ($items as $i)
                        <div class="col-span-3 lg:col-span-1">
                            <img src="{{ asset('image/'.$i->image) }}" style="width:400px;height:400px;">
                            <div class="px-2.5 py-2 border-r border-l border-b border-gray-400 ">
                                <h3 class="text-xl mb-0.5 font-semibold">
                                    {{ $i -> name }}
                                </h3>
                                <p class="text-gray-600 mt-1">
                                    {{ Str::limit($i -> description, 100) }}
                                </p>
                            </div>
                        </div>
                    @endforeach
                </div>
            </div>

        </div>
    </div>
</x-guest-layout>
