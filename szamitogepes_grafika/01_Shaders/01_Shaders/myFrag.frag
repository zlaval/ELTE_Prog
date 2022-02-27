#version 130

// bemeneti változó - most a vertex shader-ből (vagyis ottani out)
in vec4 vs_out_col;

// kimeneti változó - a fragment színe
out vec4 fs_out_col;

void main()
{
	//				  R, G, B, A
	fs_out_col = vs_out_col;

	//				  R, G, B, A
	//fs_out_col = vec4(1, 1, 1, 1);
}


// 1. feladat: rajzoljuk ki fehérrel a téglalapot!

// 2. feladat: rajzoljuk ki az origó középpontú, 1 sugarú kört! Mit kell tenni, ha nem a
//    körlapot, hanem csak a körvonalat akarjuk? Eml.: discard eldobja a fragmentet

// 3. feladat: uniform változók - animált kör sugár!

// 4. feladat: komplex számok....
