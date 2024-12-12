import yaml

data = yaml.safe_load(open("tree-2.yaml","r"))

def print_yaml(yaml, tabs = 0):
    for key in yaml:
        if key == 'o':
            for i in yaml['o']:
                print_yaml(i, tabs+1)
            return
        for _ in range(tabs):
            print(end="\t")
        print(f"{key}: {yaml[key]}")

def generate_solution(yaml, stack):
    solution = yaml['s']
    # strings
    name_str = "generate-solution"
    requirement_str = ""
    for past in stack:
        name_str += f">{past}"
        requirement_str += f"\tAnswer( name == \"{past}\" )\n"
    requirement_str = requirement_str[:-1]
    rule = f"""
rule "{name_str}"
when
{requirement_str}
then
	Solution solution = new Solution("{solution}");
    insert(solution);
end
"""
    print(rule)

def generate_questions(yaml, stack = []):
    name = yaml['i']
    stack = stack + [name]
    if 'q' not in yaml:
        generate_solution(yaml, stack)
        return
    # collect
    question = yaml['q']
    isMultiple = str(False).lower() # TODO
    answers = []
    for option in yaml['o']:
        answers.append((option['i'], option['a']))
    # strings
    name_str = "generate-question"
    requirement_str = ""
    for past in stack:
        name_str += f">{past}"
        requirement_str += f"\tAnswer( name == \"{past}\" )\n"
    requirement_str = requirement_str[:-1]
    answers_str = ""
    for answer_name, answer in answers:
        answers_str += f'\tquestion.getAnswers().add(new Answer("{answer_name}", "{answer}"));\n'
    rule = f"""
rule "{name_str}"
when
{requirement_str}
then
	Question question = new Question({name}, "{question}", {isMultiple}, new ArrayList());

{answers_str}
	insert(question);
end
"""
    print(rule)
    # recursion
    for option in yaml['o']:
        generate_questions(option, stack)

generate_questions(data)
