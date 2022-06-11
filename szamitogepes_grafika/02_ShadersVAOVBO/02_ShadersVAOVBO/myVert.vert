#version 130

// VBO-ból érkező változók
in vec3 vs_in_pos;
in vec3 vs_in_col;

uniform float scale=1;

// a pipeline-ban tovább adandó értékek
out vec3 vs_out_pos;
out vec3 vs_out_col;

void main()
{
	gl_Position = vec4( vs_in_pos*scale, 1 );
	vs_out_pos = vs_in_pos*scale;
	vs_out_col = vs_in_col;
}