<x-guest-layout>

    <div class="container mx-auto p-3 overflow-hidden min-h-screen">
        <div class="mb-5">
            <h1 class="font-semibold text-3xl mb-4">{{$item->name}}</h1>
            <p class="mb-2">Tárgy módosítása.</p>
            <a href="{{ url()->previous()}}" class="text-blue-400 hover:text-blue-600 hover:underline"><i
                    class="fas fa-long-arrow-alt-left"></i> Vissza</a>
        </div>

        <form
            {{--            x-data="{ name: '{{ old('name', $item -> name) }}', description: '{{ old('description', $item -> description)}}', display: '{{old('display'),$label->display  }}'}"--}}
            action="{{ route('items.update',$item) }}" method="POST" enctype="multipart/form-data">
            @csrf
            @method('PATCH')
            <div class="container mx-auto p-3 ">
                <div class="flex flex-col gap-5">
                    <div class="w-full">
                        <label for="name" class="block font-medium text-gray-700">Tárgy neve</label>
                        <input
                            type="text"
                            name="name"
                            value="{{ $item->name }}"
                            class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300">
                    </div>
                    <div class="w-full">
                        <label class="block font-medium text-gray-700">Tárgy leírása</label>
                        <textarea
                            name="description"
                            rows="5"
                            class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300">{{$item->description}}</textarea>
                    </div>
                    <div class="w-full grid ">
                        @if($item->image)
                            <div class="gap-5">
                                <label class="block font-medium text-gray-700">Eredeti kép</label>
                                <img class="object-contain " src="{{ asset('image/'.$item->image) }}"
                                     style="height:200px;">
                            </div>
                        @endif
                        <div>
                            <label class="block font-medium text-gray-700">Kép feltöltése</label>
                            <input
                                type="file"
                                name="image"
                                class="mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300">
                        </div>
                    </div>
                </div>
                <div class="w-full my-5">
                    <label class="block font-medium text-gray-700">Címkék</label>
                    @foreach($labels as $l)
                        <div class="flex flex-row pb-1">
                            <input type="checkbox" class="my-0.5 mx-1" name="labels[]" value="{{ $l -> id }}"
                            {{$item->labels->contains($l)? 'checked': '' }}
                            >
                            <div class="py-0.5 px-1.5 font-semibold text-sm"
                                 style="background-color: {{ $l -> color }}; ">{{ $l -> name}}</div>
                        </div>
                    @endforeach
                </div>
            </div>

            <button type="submit"
                    class="mt-2 bg-blue-500 hover:bg-blue-600 text-gray-100 font-semibold px-2 py-1 text-xl">
                Mentés
            </button>
        </form>
    </div>

</x-guest-layout>
