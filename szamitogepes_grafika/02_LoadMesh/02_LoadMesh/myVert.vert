#version 130

// VBO-ból érkezõ változók
in vec3 vs_in_pos;
in vec2 vs_in_tex0;
in vec3 vs_in_norm;

// a pipeline-ban tovább adandó értékek
out vec3 vs_out_pos;
out vec2 vs_out_tex0;
out vec3 vs_out_norm;

// shader külsõ paraméterei - most a három transzformációs mátrixot külön-külön vesszük át
uniform mat4 MVP;

uniform mat4 world;
uniform mat4 WorldIT;


void main()
{
	gl_Position = MVP * vec4( vs_in_pos, 1 );
	vs_out_pos  = (world * vec4(vs_in_pos, 1)).xyz;
	vs_out_norm = (WorldIT * vec4( vs_in_norm, 1 )).xyz;
	vs_out_tex0 = vs_in_tex0;
}