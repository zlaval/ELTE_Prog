#version 140

// pipeline-ból bejövő per-fragment attribútumok
in vec3 vs_out_pos;
in vec3 vs_out_norm;
in vec2 vs_out_tex;

// kimenő érték - a fragment színe
out vec4 fs_out_col;

// irány fényforrás: fény iránya
uniform vec3 light_dir = vec3(-1,-1,-1);

// fénytulajdonságok: ambiens, diffúz, spekuláris
uniform vec3 La = vec3(0.4, 0.4, 0.4);
uniform vec3 Ld = vec3(0.4, 0.6, 0.6);
uniform vec3 Ls = vec3(0.9, 0.9, 0.9);

// anyagtulajdonságok: ambiens, diffúz, spekuláris
uniform vec3 Ka = vec3(0.2, 0.4, 0.6);
uniform vec3 Kd = vec3(0.2, 0.4, 0.6);
uniform vec3 Ks = vec3(0.4, 0.8, 1.0);

uniform vec3 eyePos;

//uniform sampler2D texImage;

void main()
{	
	//
	// ambiens szín számítása
	//

	vec3 ambient = Ka;

	//
	// diffúz szín számítása
	//	
	/* segítség:
	    - normalizálás: http://www.opengl.org/sdk/docs/manglsl/xhtml/normalize.xml
	    - skaláris szorzat: http://www.opengl.org/sdk/docs/manglsl/xhtml/dot.xml
	    - clamp: http://www.opengl.org/sdk/docs/manglsl/xhtml/clamp.xml
	*/

	vec3 l=normalize(light_dir);
	vec3 n=normalize(vs_out_norm);
	float di=clamp(dot(-l,n),0,1);
	vec3 diffuse=di*Ld*Kd;


	//vec3 diffuse = ;

	//
	// fényfoltképző szín
	//
	/* segítség:
		- reflect: http://www.opengl.org/sdk/docs/manglsl/xhtml/reflect.xml
				reflect(beérkező_vektor, normálvektor);
		- pow: http://www.opengl.org/sdk/docs/manglsl/xhtml/pow.xml
				pow(alap, kitevő);
	*/

	vec3 r=reflect(l,n);
	vec3 toEye=normalize(eyePos - vs_out_pos);
	float si= pow(clamp(dot(r,toEye),0,1),16);
	vec3 specular = si*Ls*Ks ;
	
	//
	// a fragment végső színének meghatározása
	//

	//fs_out_col = vec4(ambient + diffuse + specular, 1);

	// felületi normális
	//fs_out_col = vec4(vs_out_norm, 1);

	fs_out_col = vec4(ambient+diffuse+specular, 1);

	// textúrával
	//vec4 textureColor = texture(texImage, vs_out_tex);
	//fs_out_col = vec4(ambient + diffuse + specular, 1) * textureColor;
}
